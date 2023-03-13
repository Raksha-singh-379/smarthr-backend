import dayjs from 'dayjs/esm';
import { ILeaveType } from 'app/entities/leave-type/leave-type.model';
import { IEmploymentType } from 'app/entities/employment-type/employment-type.model';

export interface ILeavePolicy {
  id: number;
  isCarryForword?: boolean | null;
  genderLeave?: number | null;
  totalLeave?: number | null;
  maxLeave?: number | null;
  hasproRataLeave?: boolean | null;
  description?: string | null;
  refTable?: string | null;
  refTableId?: number | null;
  companyId?: number | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  leaveType?: Pick<ILeaveType, 'id' | 'leaveType'> | null;
  employmentType?: Pick<IEmploymentType, 'id' | 'name'> | null;
}

export type NewLeavePolicy = Omit<ILeavePolicy, 'id'> & { id: null };
